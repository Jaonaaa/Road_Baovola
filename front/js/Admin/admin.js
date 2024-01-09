import { setUpNavbar } from "../components/Navbar.js";
import { handleSwitcher } from "../components/SwitcherFormAdmin.js";
import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";
import { setUpFormTypeMateriauxQuality } from "./FormRoad.js";
import { handleQualityForm } from "./quality.js";

handleType();
handleMateriaux();
handleSwitcher();
handleQualityForm();
getMaterial();
setUpNavbar();
handleSizeForm();
handleTypeMateriauxQuality();

function handleTypeMateriauxQuality() {
  setUpFormTypeMateriauxQuality();
}

function handleSizeForm() {
  let form = document.getElementById("size_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = document.getElementById("name_size");
    if (name.value.trim() === "") {
      createSidePopUp("Name can't be empty", "error");
    } else {
      let res = await send({ name: name.value }, "road/size");
      createSidePopUp(res.details, res.status);
    }
  });
}

function handleType() {
  let form = document.getElementById("type_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = document.getElementById("name_type");
    let res = await send({ name: name.value }, "road/type");

    createSidePopUp(res.details, res.status);
  });
}

function handleMateriaux() {
  let form = document.getElementById("material_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = document.getElementById("name_material");
    let res = await send({ name: name.value }, "materiaux");
    getMaterial();
    createSidePopUp(res.details, res.status);
  });
}

async function getMaterial() {
  let material = await get("materiaux");
  let types_road = await get("materiaux/types");

  let materials = document.getElementById("materials");
  let materials_price = document.getElementById("materials_price");

  let types_select = document.getElementById("types");
  materials.innerHTML = "";
  types_select.innerHTML = "";

  material.data.forEach((materiaux) => {
    materials.innerHTML += `<div class="box item" idMateriaux="${materiaux.id}" nameMateriaux="${materiaux.name}"> ${materiaux.name} </div>`;
    materials_price.innerHTML += `<option value="${materiaux.id}"> ${materiaux.name} </option>`;
  });

  types_road.data.forEach((type) => {
    types_select.innerHTML += `<option value="${type.id}"> ${type.name} </option>`;
  });

  handleFormMaterial();
  handlePricedMaterial();
}

function handlePricedMaterial() {
  let form = document.getElementById("price_material");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let idMateriaux = +form.querySelector("#materials_price").value;
    let price = +form.querySelector("#price").value;
    let data = {
      materiaux: {
        id: idMateriaux,
      },
      price: price,
    };
    let res = await send(data, "road_mat/price/");
    createSidePopUp(res.details, res.status);
  });
}

function handleFormMaterial() {
  let boxes = document.querySelectorAll(".box");
  boxes.forEach((box) => {
    box.addEventListener("click", () => {
      if (!box.classList.contains("selected_item")) box.classList.add("selected_item");
      else box.classList.remove("selected_item");
    });
  });
  handleTypeMaterial();
  //
}

function handleTypeMaterial() {
  let form = document.getElementById("type_material_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let boxes_selected = document.querySelectorAll(".selected_item");
    let type = document.getElementById("types").value;
    let boxes__ = Array.from(boxes_selected).map((box) => ({
      materiaux: {
        id: +box.getAttribute("idMateriaux"),
      },
      type: {
        id: +type,
      },
    }));
    let res = await send(boxes__, "road/type_materiaux");
    createSidePopUp(res.details, res.status);
  });
}
