export const URL = "http://localhost:8087/";

export async function send(form, url) {
  let res = await alaivoPost(url, JSON.stringify(form));
  console.log(res);
  return res;
}

export async function get(url) {
  let res = await alaivoGet(url);
  return res;
}

const rebuildURL = (url = "") => {
  if (url.indexOf(":njdev/") !== -1) return url;
  else return URL + url;
};

export const getHeaderAuthJWT = () => ({
  headers: {
    Authorization: "Bearer " + localStorage.getItem("token"),
    "Content-Type": "application/json",
  },
});

export const alaivoGet = async (url = "", options, noAuth = false) => {
  let auth = !noAuth ? getHeaderAuthJWT() : null;

  return new Promise((resolve, reject) => {
    fetch(rebuildURL(url), {
      method: "GET",
      ...auth,
      ...options,
    })
      .then((response) => response.json())
      .then((responseData) => {
        resolve(responseData);
      })
      .catch((error) => reject(error));
  });
};

export const alaivoPost = (url = "", data, options, noAuth = false) => {
  let auth = !noAuth ? getHeaderAuthJWT() : null;

  return new Promise((resolve, reject) => {
    fetch(rebuildURL(url), {
      method: "POST",
      body: data,
      ...auth,
      ...options,
    })
      .then((response) => response.json())
      .then((responseData) => {
        resolve(responseData);
      })
      .catch((error) => reject(error));
  });
};
