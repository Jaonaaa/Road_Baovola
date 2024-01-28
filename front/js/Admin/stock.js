import { setUpNavbar } from "../components/Navbar.js";
import { handleSwitcher } from "../components/SwitcherFormAdmin.js";
import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";

setUpNavbar();
handleSwitcher();
handleFormAddStock();
handleFormAddSupplier();
handleFormBuyRoad();
formList();

async function formList() {
  let container = document.getElementById("list_stock_container");
  let list_stock = await get("stock");
  container.innerHTML = "";
  list_stock.data.forEach((stock) => {
    container.innerHTML += `<div class="item"> ${stock.materiaux.name} | ${stock.quantity}</div>`;
  });
}

async function handleFormBuyRoad() {
  let roadType = await get("road/type_quality");
  let entreprises = await get("client");
  let roads_types = document.getElementById("roads_types");
  let entreprises_list = document.getElementById("entreprises");

  console.log(entreprises);
  roadType.data.forEach((road) => {
    roads_types.innerHTML += `<option value="${road.id}"> ${road.type.name} | ${road.quality.name} | ${road.size.name} </option>`;
  });
  entreprises.data.forEach((road) => {
    entreprises_list.innerHTML += `<option value="${road.id}">  ${road.name} | ${road.type_entreprise.name}  </option>`;
  });
  formBuyRoad();
}

function formBuyRoad() {
  let form = document.getElementById("buy_road");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let road = form.querySelector("#roads_types").value;
    let quantity = form.querySelector("#quantity_road_to_buy").value;
    let id_entreprise = form.querySelector("#entreprises").value;

    let data = [
      {
        roadTypeQuality: {
          id: road,
        },
        entreprise: {
          id: id_entreprise,
        },
        quantity: quantity,
      },
    ];
    let res = await send(data, "trade");
    createSidePopUp(res.details, res.status);
  });
}

function handleFormAddSupplier() {
  let form = document.getElementById("add_supplier");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    let name = form.querySelector("#name_supplier").value;
    let data = {
      name: name,
    };
    let res = await send(data, "stock/supplier");
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormAddStock() {
  let material = await get("materiaux");
  let supplier = await get("stock/supplier");

  let materials_select = document.getElementById("materials_stock");
  let supplier_select = document.getElementById("supplier_stock");

  materials_select.innerHTML = "";
  supplier_select.innerHTML = "";

  material.data.forEach((mat) => {
    materials_select.innerHTML += `<option value="${mat.id}"> ${mat.name} </option>`;
  });

  supplier.data.forEach((mat) => {
    supplier_select.innerHTML += `<option value="${mat.id}"> ${mat.name} </option>`;
  });
  formAddStock();
}

function formAddStock() {
  let form = document.getElementById("add_stock");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    let material = form.querySelector("#materials_stock").value;
    let supplier = form.querySelector("#supplier_stock").value;
    let qt = form.querySelector("#quantity_stock").value;

    let data = {
      materiaux: {
        id: material,
      },
      supplier: {
        id: supplier,
      },
      quantity: qt,
    };
    let res = await send(data, "stock");
    createSidePopUp(res.details, res.status);
  });
}
