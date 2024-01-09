import { setUpNavbar } from "../components/Navbar.js";
import { get, send } from "../utils/fetchers.js";

handleSearch();
setUpNavbar();

function handleSearch() {
  let form = document.getElementById("search_form");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    let min = +document.getElementById("min").value;
    let max = +document.getElementById("max").value;
    let res = await get(`road_mat/mat/compare/${min}/${max}`);
    console.log(res);
    addTableData(res.data);
  });
}

function addTableData(data) {
  let table = document.getElementById("table_result");

  table.innerHTML = `
  <tr>
  <th>Type</th>
  <th>Qualité</th>
  <th>Taille</th>
  <th>Prix</th>
</tr>
  `;

  data.forEach((road) => {
    table.innerHTML += `
    <tr>
        <td>${road.roadTypeQuality.type.name} </td>
        <td>${road.roadTypeQuality.quality.name}</td>
        <td>${road.roadTypeQuality.size.name}</td>
        <td>${road.price} Ar</td>
    </tr>
        `;
  });
}
