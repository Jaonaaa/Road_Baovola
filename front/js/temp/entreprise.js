import { setUpNavbar } from "../components/Navbar.js";
import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";
import { handleSwitcher } from "../components/SwitcherFormAdmin.js";

setUpNavbar();
handleSwitcher();
handleAddEmpType();
handleFormEmployer();

async function handleFormEmployer() {
  let outils = await get("client/type");
  let outils_list = document.getElementById("entreprises_types");
  outils.data.forEach((outil) => {
    outils_list.innerHTML += `<option value="${outil.id}"> ${outil.name}  </option>`;
  });
  handleAddEntrepruse();
}

function handleAddEntrepruse() {
  let form = document.getElementById("add_entreprise");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = form.querySelector("#name_entre").value;
    let id = form.querySelector("#entreprises_types").value;
    let data = {
      name: name,
      type_entreprise: { id: id },
    };
    let res = await send(data, `client`);
    createSidePopUp(res.details, res.status);
  });
}

function handleAddEmpType() {
  let form = document.getElementById("form_add_type_entreprise");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = form.querySelector("#name_type_ent").value;
    let data = {
      name: name,
    };
    let res = await send(data, `client/type`);
    createSidePopUp(res.details, res.status);
  });
}
