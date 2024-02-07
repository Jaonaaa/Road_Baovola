import { setUpNavbar } from "../components/Navbar.js";
import { get, send } from "../utils/fetchers.js";

setUpNavbar();
getAllMateriaux();
handleShowPriceMaterial();
handleEmployer();
handlePrixDeVente();
handleEmployerWorking();
handleGrades();
handleAllListRoad();
let prices_mat = null;

async function getAllMateriaux() {
  let materiaux = await get("materiaux");
  let filter = document.getElementById("filter");
  addMateriaux(filter, materiaux.data);
}

/**
 *
 * @param {HTMLElement} filter
 * @param {[] materiaux}
 */
function addMateriaux(filter, materiaux) {
  filter.innerHTML = "";
  materiaux.forEach((materiau) => {
    filter.innerHTML += `
        <div class="filter_item" idMateriaux="${materiau.id}"> ${materiau.name}</div>`;
  });
  let filters = document.querySelectorAll(".filter_item");
  filters.forEach((filter) => {
    filter.addEventListener("click", async () => {
      let road = await get("road_mat/mat/" + +filter.getAttribute("idMateriaux"));
      let price_material = await get("materiaux/price");
      addItems(road.data, price_material.data);
    });
  });
}

function addItems(roads, price_material) {
  let list = document.querySelector(".list_container");
  list.innerHTML = "";
  roads.forEach((road) => {
    let roadData = road.roadTypeQuality;
    let item = document.createElement("div");
    item.classList.add("item");
    item.innerHTML = `
    <div class="type_name"> <div class="label"> Type : </div>  ${roadData.type.name} </div>
    <div class="type_name"> <div class="label"> Size :</div>  ${roadData.size.name} </div>
    <div class="type_name"> <div class="label"> Quality : </div>  ${roadData.quality.name} </div>
    <br> 
    `;
    let added = false;
    let bottom_block = document.createElement("div");
    bottom_block.classList.add("bottom_block");

    item.addEventListener("click", async () => {
      if (!added) {
        let res = await send(roadData, "road_mat/mat/");
        let pp = res.data.map((re) => `<div class="qt"> ${re.materiaux.name} : ${re.quantity} </div>`);
        let rows = "";
        pp.forEach((row) => (rows += row));
        ///
        let mainoeuvre = await get("outils/main_oeuvre/" + roadData.id);

        bottom_block.innerHTML += `
        <div class="block_in">
        <div class="title_block"> Matière première </div>
            <div class="type_"> 
            ${rows}
            </div>
            <div class="total"> 
            Total : <br>
            <span class="price_"> ${getPriceMaterial(res.data, price_material)} Ar</span>
            </div>
         </div>
         ${mainOeuvreContent(mainoeuvre.data)}
        `;

        item.appendChild(bottom_block);

        added = true;
      }
    });
    list.appendChild(item);
  });
}

function mainOeuvreContent(mainoeuvre) {
  let block = document.createElement("div");
  block.classList.add("block_in");
  block.classList.add("block_in_expanded");
  let total = 0;
  let rows = "";
  mainoeuvre.forEach((m_o) => {
    total += m_o.outils.salaire * m_o.time_to_work;
    rows += `<div class="qt"><span class="label"> ${m_o.outils.name} : ${m_o.outils.salaire} Ar / h </span> <span class="right"> ${m_o.time_to_work} h </span></div>`;
  });

  block.innerHTML += `
  <div class="title_block"> Employer </div>
  <div class="type_"> 
  ${rows}
  </div>
  <div class="total"> 
  Total : <br>
  <span class="price_"> ${total} Ar</span>
  
  `;

  return block.outerHTML;
}

function getPriceMaterial(tab_materiaux, tab_materiaux_priced) {
  let total = 0;
  tab_materiaux.forEach((mat) => {
    let mat_find = tab_materiaux_priced.filter((row) => row.materiaux.id === mat.materiaux.id);
    total += mat.quantity * mat_find[0].price;
  });
  return total;
}

function handleShowPriceMaterial() {
  let btn = document.getElementById("show_price");
  let container = document.getElementById("prices_container");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let price_material = await get("materiaux/price");
    prices_mat = price_material;
    let blocks = "";
    price_material.data.forEach((block) => {
      blocks += `
          <div class="item">
            <div class="label">${block.materiaux.name}</div>
            <div class="price">${block.price} Ar</div>
          </div>
          `;
    });
    console.log(blocks);
    container.innerHTML = blocks;
  });
}

function handleEmployer() {
  let btn = document.getElementById("show_employe");
  let container = document.getElementById("employer_container");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let emps = await get("outils");
    console.log(emps);
    let blocks = "";
    emps.data.forEach((block) => {
      blocks += `
          <div class="item">
            <div class="label">${block.name}</div>
            <div class="price">${block.salaire} Ar</div>
          </div>
          `;
    });
    container.innerHTML = blocks;
  });
}

function handleAllListRoad() {
  let btn = document.getElementById("show_all_road");
  let container = document.getElementById("road_container_list");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let roads = await get("road/type_quality");
    console.log(roads);
    let blocks = "";
    roads.data.forEach((block) => {
      blocks += `
          <div class="item">
            <div class="label">${block.type.name} | ${block.quality.name} | ${block.size.name}</div>
          </div>
          `;
    });
    container.innerHTML = blocks;
  });
}
function handlePrixDeVente() {
  let btn = document.getElementById("show_price_vente");
  let container = document.getElementById("price_road_container");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let prices = await get("outils/price_vente");
    let blocks = "";
    prices.data.forEach((block) => {
      let roadTypeQuality = block.roadTypeQuality;
      let name = roadTypeQuality.type.name + " | " + roadTypeQuality.size.name + " | " + roadTypeQuality.quality.name;
      blocks += `
          <div class="item">
            <div class="label">${name}</div>
            <div class="price">${block.price} Ar</div>
          </div>
          `;
    });
    container.innerHTML = blocks;
  });
}

function handleEmployerWorking() {
  let btn = document.getElementById("show_employer_working");
  let container = document.getElementById("show_employer_working_container");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let emp = await get("emp");
    let blocks = "";
    emp.data.forEach((block) => {
      let grade = block.grade;
      let upgrade_salaire = +(grade ? grade.upgrade_salaire : 1);
      let salaire_actuelle = block.outils.salaire * upgrade_salaire;
      blocks += `
      <div class='block_emp'>
          <div class="item">
            <div class="label">Nom : </div>
            <div class="price"> ${block.name}</div>
          </div>
          <div class="item">
            <div class="label"> Poste : </div>
            <div class="price"> ${block.outils.name}</div>
          </div>
          <div class="item">
            <div class="label"> Grade : </div>
            <div class="price"> ${grade ? grade.name : " aucun "}</div>
          </div>
          <div class="item">
            <div class="label"> Salaire  basique : </div>
            <div class="price"> ${block.outils.salaire} Ar </div>
        </div>
        <div class="item">
            <div class="label"> Salaire actuelle : </div>
            <div class="price"> ${salaire_actuelle} Ar </div>
        </div>
        <div class="item">
              <div class="label"> Date d'embauche : </div>
              <div class="price"> ${block.date_embauche.replace("T00:00:00.000+00:00", "")}  </div>
        </div>
    </div>
          `;
    });
    container.innerHTML = blocks;
  });
}

function handleGrades() {
  let btn = document.getElementById("show_grades");
  let container = document.getElementById("show_grades_container");

  btn.addEventListener("click", async () => {
    container.innerHTML = "";
    let grades = await get("emp/grades");
    let blocks = "";
    console.log(grades);
    grades.data.forEach((block) => {
      blocks += `
      <div class='block_emp'>
            <div class="item">
              <div class="label">Nom : </div>
              <div class="price"> ${block.name}</div>
            </div>
            <div class="item">
              <div class="label">Upgrade de salaire : </div>
              <div class="price"> x${block.upgrade_salaire}</div>
            </div>
            <div class="item">
              <div class="label"> Upgrader après : </div>
              <div class="price"> ${block.upgrade_after}</div>
            </div>
            </div>
       `;
    });
    container.innerHTML = blocks;
  });
}
