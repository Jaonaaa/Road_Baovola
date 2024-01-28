import { setUpNavbar } from "../components/Navbar.js";
import { createMiddlePopUp, createSidePopUp } from "../utils/PopUp.js";
import { get, send } from "../utils/fetchers.js";

handleSearch();
setUpNavbar();
let mode = "normal";

handleMode();

function handleMode() {
  let btn = document.getElementById("change_mode");
  btn.addEventListener("click", () => {
    if (mode == "normal") {
      mode = "benefice";
      createSidePopUp("Mode benefice on", "ok");
    } else if (mode == "benefice") {
      mode = "normal";
      createSidePopUp("Mode normal on", "ok");
    }
  });
}

function handleSearch() {
  let form = document.getElementById("search_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let min = +document.getElementById("min").value;
    let max = +document.getElementById("max").value;
    if (mode == "normal") {
      let res = await get(`road_mat/mat/compare/${min}/${max}`);
      console.log(res);
      addTableData(res.data);
    } else if (mode == "benefice") {
      let res = await get(`vente/benefice/between/${min}/${max}`);
      console.log(res);
      addTableData(res.data);
    }
  });
}

function addTableData(data) {
  let table = document.createElement("table");
  table.setAttribute("id", "table_result");
  // let table = document.getElementById("table_result");

  table.innerHTML =
    mode === "normal"
      ? `
  <tr>
  <th>Type</th>
  <th>Qualité</th>
  <th>Taille</th>
  <th>Prix</th>
  </tr>
  `
      : `
  <tr>
  <th>Type</th>
  <th>Qualité</th>
  <th>Taille</th>
  <th>Bénefice</th>
  <th>Prix de vente</th>
  <th>Prix de revient</th>
  <th>Prix matières premières </th>
  <th>Salaire employer </th>
  </tr>
  
  `;
  data.forEach((road) => {
    table.innerHTML +=
      mode === "normal"
        ? `
    <tr>
        <td>${road.roadTypeQuality.type.name} </td>
        <td>${road.roadTypeQuality.quality.name}</td>
        <td>${road.roadTypeQuality.size.name}</td>
        <td>${road.price} Ar</td>
    </tr>
        `
        : `
        <tr>
        <td>${road.roadTypeQuality.type.name} </td>
        <td>${road.roadTypeQuality.quality.name}</td>
        <td>${road.roadTypeQuality.size.name}</td>
        <td>${road.benefice} Ar</td>
        <td>${road.prix_de_vente} Ar</td>
        <td>${road.prix_de_revient} Ar</td>
        <td>${road.prix_matiere_premiere} Ar</td>
        <td>${road.prix_employer} Ar</td>

    </tr>
        
        `;
  });

  createMiddlePopUp(table);
}
