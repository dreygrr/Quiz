document.addEventListener("DOMContentLoaded", () => {
  const btnNovaPergunta = document.querySelector("newQuestion");
  const btnTitle = btnNovaPergunta.querySelector(".btn-timer-title");

  if (btnNovaPergunta && btnTitle) {
    btnNovaPergunta.disabled = true;
    btnNovaPergunta.classList.add('disabled');
    
    btnTitle.innerHTML = "3";

    setButtonTimer(btnTitle);

    setTimeout(() => {
      btnNovaPergunta.disabled = false;
      btnNovaPergunta.classList.remove('disabled');
    }, 3000);
  }
});



const setButtonTimer = (btnTitleTag) => {
  let i = 2;

  const btnTimer = setInterval(() => {
    btnTitleTag.innerHTML = i; 
    i--; 
  }, 1000); 

  setTimeout(() => {
    clearInterval(btnTimer); 
    btnTitleTag.innerHTML = "Nova pergunta"; 
  }, 3000);
};