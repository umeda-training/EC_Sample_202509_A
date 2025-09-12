document.addEventListener("DOMContentLoaded", function() {
  // すべてのカードを取得
  const cards = document.querySelectorAll(".item-card");

  // 各カードにクリックイベントを設定
  cards.forEach(card => {
    card.style.cursor = "pointer"; // ホバー時にクリック可能っぽくする
    card.addEventListener("click", function() {
      const url = card.dataset.url; // data-url属性から取得
      if(url) {
        window.location.href = url; // カード全体をクリックしたら遷移
      }
    });

    // オプション：ホバーで少し影や色を変える場合
    card.addEventListener("mouseenter", () => {
      card.style.boxShadow = "0 4px 8px rgba(0,0,0,0.2)";
      card.style.transition = "box-shadow 0.3s";
    });
    card.addEventListener("mouseleave", () => {
      card.style.boxShadow = "none";
    });
  });
});


document.addEventListener('DOMContentLoaded', () => {
    const header = document.getElementById('header');

    document.addEventListener('mousemove', (e) => {
        if (e.clientY <= 50) { // 画面上部50px以内にマウスがある場合
            header.classList.add('show');
        } else {
            header.classList.remove('show');
        }
    });
});