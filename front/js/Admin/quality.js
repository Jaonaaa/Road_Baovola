import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";

export function handleQualityForm() {
  let form = document.getElementById("quality_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = document.getElementById("name_quality");
    let res = await send({ name: name.value }, "qt");
    createSidePopUp(res.details, res.status);
  });
  setUpQualityTypeForm();
}

export async function setUpQualityTypeForm() {
  let quality = await get("qt");
  let types_road = await get("materiaux/types");

  let form = document.getElementById("road_quality_form");

  let selectType = form.querySelector("#type_road");
  let selectQuality = form.querySelector("#quality_road");

  quality.data.forEach((quality) => {
    selectQuality.innerHTML += `<option class="box item" value='${quality.id}'> ${quality.name} </option>`;
  });

  types_road.data.forEach((types) => {
    selectType.innerHTML += `<option class="box item" value='${types.id}'> ${types.name} </option>`;
  });
  handleFormQualityTypes(form, selectType, selectQuality);
}

/**
 *
 * @param {HTMLElement} form
 * @param {HTMLElement} selectType
 * @param {HTMLElement} selectQuality
 */
function handleFormQualityTypes(form, selectType, selectQuality) {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let time_m_square = form.querySelector("#time_m_square").value;
    let timeRoad = {
      quality: { id: +selectQuality.value },
      type: { id: +selectType.value },
      time_m_square: +time_m_square,
    };
    let res = await send(timeRoad, "road/time/qt/type");
    createSidePopUp(res.details, res.status);
  });
}
