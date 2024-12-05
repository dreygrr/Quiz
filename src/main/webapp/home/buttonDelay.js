document.addEventListener("DOMContentLoaded", () => {
  const btnNovaPergunta = document.querySelector("#initQuiz");
  const btnTitle = btnNovaPergunta.querySelector(".btn-timer-title");

  if (btnNovaPergunta && btnTitle) {
    btnNovaPergunta.disabled = true;
    btnNovaPergunta.classList.add('disabled');
    
    btnTitle.innerHTML = "4";

    setButtonTimer(btnTitle);

    setTimeout(() => {
      btnNovaPergunta.disabled = false;
      btnNovaPergunta.classList.remove('disabled');
    }, 4000);
  }
});



const setButtonTimer = (btnTitleTag) => {
  let i = 3;

  const btnTimer = setInterval(() => {
    btnTitleTag.innerHTML = i; 
    i--; 
  }, 1000); 

  setTimeout(() => {
    clearInterval(btnTimer); 
    btnTitleTag.innerHTML = "Iniciar quiz"; 
  }, 4000);
};