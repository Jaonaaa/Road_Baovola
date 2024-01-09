export function handleSwitcher() {
  let switcher = document.getElementById("switcher_form");
  let tos = switcher.querySelectorAll(".to");
  tos.forEach((to) => {
    to.addEventListener("click", () => {
      if (!to.classList.contains("to_selected")) {
        let idForm = to.getAttribute("idForm");
        let formSelected = document.getElementById(idForm);
        formSelected.classList.add("form_active");
        to.classList.add("to_selected");
        handleUnselectedTo(to);
      }
    });
  });
}

function handleUnselectedTo(toSelected) {
  let switcher = document.getElementById("switcher_form");
  let tos = switcher.querySelectorAll(".to");
  tos.forEach((to) => {
    if (to !== toSelected) {
      to.classList.remove("to_selected");
      let idForm = to.getAttribute("idForm");
      let formSelected = document.getElementById(idForm);
      formSelected.classList.remove("form_active");
    }
  });
}
