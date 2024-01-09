import { setUpNavbar } from "./components/Navbar.js";
import { get } from "./utils/fetchers.js";

handleForm();
setUpNavbar();

async function handleForm() {
  let types_road = await get("materiaux/types");
  let types_select = document.getElementById("types");
  types_road.data.forEach((type) => {
    types_select.innerHTML += `<option value="${type.id}"> ${type.name} </option>`;
  });
  handleFormRoad();
}

async function handleFormRoad() {
  let forms = document.getElementById("getPave");
  let types_select = document.getElementById("types");

  forms.addEventListener("submit", async (e) => {
    console.log("Heloo");
    e.preventDefault();
    let materiaux = await get("road/type_materiaux/" + types_select.value);
    showMaterialsTypes(materiaux.data);
  });
}

function showMaterialsTypes(materiaux) {
  let boxes = document.createElement("div");
  boxes.classList.add("list");
  materiaux.forEach((mat) => {
    let matBox = document.createElement("div");
    matBox.classList.add("item");
    matBox.innerHTML = mat.materiaux.name;
    boxes.appendChild(matBox);
  });

  let result = document.getElementById("result");
  result.innerHTML = "";
  result.appendChild(boxes);
}
