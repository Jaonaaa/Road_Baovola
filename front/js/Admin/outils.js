import { setUpNavbar } from "../components/Navbar.js";
import { handleSwitcher } from "../components/SwitcherFormAdmin.js";
import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";

setUpNavbar();
handleSwitcher();
handleAddOutils();
handleFormSellingPrice();
handleFormAddNumberBySize();
handleFormAddTimeByQuality();
handleFormMainOeuvre();

function handleAddOutils() {
  let form = document.getElementById("add_employe");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let job_name = form.querySelector("#job_outil").value;
    let salaire = +form.querySelector("#salaire_outil").value;
    let data = {
      name: job_name,
      salaire: salaire,
    };
    let res = await send(data, `outils`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormSellingPrice() {
  let roadType = await get("road/type_quality");
  let roads_types = document.getElementById("roads_types_selling");
  roadType.data.forEach((road) => {
    roads_types.innerHTML += `<option value="${road.id}"> ${road.type.name} | ${road.quality.name} | ${road.size.name} </option>`;
  });
  handleAddPrixVente();
}

function handleAddPrixVente() {
  let form = document.getElementById("add_selling_price");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let road_type_quality = +form.querySelector("#roads_types_selling").value;
    let selling_price = +form.querySelector("#selling_price").value;
    let data = {
      roadTypeQuality: { id: road_type_quality },
      price: selling_price,
    };
    let res = await send(data, `outils/sell_price`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormAddNumberBySize() {
  let road_size = await get("road/size");
  let roads_size_outils = document.getElementById("roads_size_outils");
  road_size.data.forEach((road) => {
    roads_size_outils.innerHTML += `<option value="${road.id}"> ${road.name}  </option>`;
  });
  handleAddOutilsNumberBySize();
}
function handleAddOutilsNumberBySize() {
  let form = document.getElementById("add_outils_size");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let size_id = +form.querySelector("#roads_size_outils").value;
    let number = +form.querySelector("#number_outils").value;
    let data = {
      sizeRoad: { id: size_id },
      quantity: number,
    };
    let res = await send(data, `outils/outils_by_size`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormAddTimeByQuality() {
  let road_quality = await get("qt");
  let quality_road = document.getElementById("quality_road");
  road_quality.data.forEach((road) => {
    quality_road.innerHTML += `<option value="${road.id}"> ${road.name}  </option>`;
  });
  handleAddTimeByQuality();
}

function handleAddTimeByQuality() {
  let form = document.getElementById("add_time_quality");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let quality_road = +form.querySelector("#quality_road").value;
    let time_quality = +form.querySelector("#time_quality").value;
    let data = {
      quality: { id: quality_road },
      time_to_work: time_quality,
    };
    let res = await send(data, `outils/time_by_quality`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormMainOeuvre() {
  let roadType = await get("road/type_quality");
  let roads_types = document.getElementById("road_main_oeuvre");
  let outils_select = document.getElementById("employer_main_oeuvre");

  roadType.data.forEach((road) => {
    roads_types.innerHTML += `<option value="${road.id}"> ${road.type.name} | ${road.quality.name} | ${road.size.name} </option>`;
  });

  let outils = await get("outils");
  outils.data.forEach((road) => {
    outils_select.innerHTML += `<option value="${road.id}"> ${road.name}  </option>`;
  });
  handleMainOeuvre();
}

function handleMainOeuvre() {
  let form = document.getElementById("add_main_oeuvre");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let road = +form.querySelector("#road_main_oeuvre").value;
    let employe = +form.querySelector("#employer_main_oeuvre").value;
    let time_emp = +form.querySelector("#time_main_oeuvre").value;

    let data = {
      roadTypeQuality: { id: road },
      outils: { id: employe },
      time_to_work: time_emp,
    };
    let res = await send(data, `outils/main_ouevre`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}
