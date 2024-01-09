import { setUpNavbar } from "../components/Navbar.js";
import { get, send } from "../utils/fetchers.js";

setUpNavbar();
getAllMateriaux();

// get("road_mat/mat/compare/0/50000");

async function getAllMateriaux() {
  let materiaux = await get("materiaux");
  let filter = document.getElementById("filter");
  addMateriaux(filter, materiaux.data);
  console.log(materiaux);
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
      addItems(road.data);
    });
  });
}

function addItems(roads) {
  let list = document.querySelector(".list_container");
  list.innerHTML = "";
  roads.forEach((road) => {
    let roadData = road.roadTypeQuality;
    // let qt = +road.quantity;
    let item = document.createElement("div");
    item.classList.add("item");
    item.innerHTML = `
    <div class="type_name"> <div class="label"> Type : </div>  ${roadData.type.name} </div>
    <div class="type_name"> <div class="label"> Size :</div>  ${roadData.size.name} </div>
    <div class="type_name"> <div class="label"> Quality : </div>  ${roadData.quality.name} </div>
    <br> 
    `;
    let added = false;
    item.addEventListener("click", async () => {
      if (!added) {
        let res = await send(roadData, "road_mat/mat/");
        let pp = res.data.map((re) => `<div class="qt"> ${re.materiaux.name} : ${re.quantity} </div>`);
        console.log(pp);
        item.innerHTML += `
      <div class="type_"> 
      ${pp}
      </div>
      `;
        added = true;
      }
    });
    list.appendChild(item);
  });
}
