import { setUpNavbar } from "../components/Navbar.js";
import { createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";
import { handleSwitcher } from "../components/SwitcherFormAdmin.js";

setUpNavbar();
handleSwitcher();
handleFormEmployer();
handleGrade();

function handleGrade() {
  let form = document.getElementById("form_grade");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let name = form.querySelector("#name_grade").value;
    let upgrade_salaire = +form.querySelector("#upgrade_salaire").value;
    let upgrade_after = +form.querySelector("#upgrade_after").value;

    let data = {
      name: name,
      upgrade_salaire: upgrade_salaire,
      upgrade_after: upgrade_after,
    };
    let res = await send(data, `emp/grade`);
    console.log(res);
    createSidePopUp(res.details, res.status);
  });
}

async function handleFormEmployer() {
  let outils = await get("outils");
  let outils_list = document.getElementById("outils_list");
  outils.data.forEach((outil) => {
    outils_list.innerHTML += `<option value="${outil.id}"> ${outil.name}  </option>`;
  });
  handleAddEmp();
}

function handleAddEmp() {
  let form = document.getElementById("add_emp");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let outils_id = +form.querySelector("#outils_list").value;
    let date_embauche = form.querySelector("#date_embauche").value;
    let name = form.querySelector("#name_emp").value;
    let data = {
      outils: { id: outils_id },
      date_embauche: date_embauche,
      name: name,
    };
    let res = await send(data, `emp`);
    createSidePopUp(res.details, res.status);
  });
}
