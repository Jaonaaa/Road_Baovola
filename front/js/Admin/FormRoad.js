import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";

export function setUpFormTypeMateriauxQuality() {
  let form = document.getElementById("road_type_quality_materiaux_form");
  getDataTypeMateriauxQuality(form);
  handleFormTypeMateriauxQuality(form);
}

/**
 *
 * @param {HTMLElement} form
 */
function handleFormTypeMateriauxQuality(form) {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let types_select = +form.querySelector("#types_road").value;
    let quality_select = +form.querySelector("#quality_road").value;
    let size_select = +form.querySelector("#size_road").value;
    ///
    let select_types = form.querySelector("#list_select_materiaux");
    let materials = select_types.querySelectorAll(".row");

    let materialsQT = [];
    materials.forEach((material) => {
      let idMateriaux = +material.querySelector(".materiaux_label").getAttribute("idmateriaux");
      let qt = +material.querySelector("input").value;
      materialsQT.push({
        materiaux: { id: +idMateriaux },
        quantity: qt,
        roadTypeQuality: {
          type: { id: types_select },
          quality: { id: quality_select },
          size: { id: size_select },
        },
      });
    });
    ////
    ////
    let res = await send(materialsQT, "road_mat");
    console.log(materialsQT);
    createSidePopUp(res.details, res.status);
  });
}

async function getDataTypeMateriauxQuality(form) {
  let types_road = await get("materiaux/types");
  let qualities = await get("qt");
  let sizes = await get("road/size");

  let types_select = form.querySelector("#types_road");
  let quality_select = form.querySelector("#quality_road");
  let size_select = form.querySelector("#size_road");

  quality_select.innerHTML = "";
  types_select.innerHTML = "";
  size_select.innerHTML = "";

  types_road.data.forEach((types) => {
    types_select.innerHTML += `<option value="${types.id}"> ${types.name} </option>`;
  });

  qualities.data.forEach((quality) => {
    quality_select.innerHTML += `<option value="${quality.id}"> ${quality.name} </option>`;
  });

  sizes.data.forEach((size) => {
    size_select.innerHTML += `<option value="${size.id}"> ${size.name} </option>`;
  });

  if (types_road.data.length <= 0) {
    createSidePopUp("No type of road available");
  } else {
    let materials = await get("road/type_materiaux/" + types_road.data[0].id);
    addMateriauxSelection(materials.data);
    types_select.addEventListener("input", async () => {
      let id_type = +types_select.value;
      materials = await get("road/type_materiaux/" + id_type);
      addMateriauxSelection(materials.data);
    });
  }
}

function addMateriauxSelection(materials) {
  let list = document.getElementById("list_select_materiaux");
  list.innerHTML = "";
  materials.forEach((material) => {
    list.innerHTML += `
    <div class="row">
    <div class="materiaux_label" idMateriaux="${material.materiaux.id}">${material.materiaux.name}</div>
    <div class="qt">
      <input type="number" name="qt" value="0" />
    </div>
  </div>
    `;
  });
}
