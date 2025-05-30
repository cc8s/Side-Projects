<style>
  <link rel="stylesheet" href="../css/배경.css">
  <link rel="stylesheet" href="../css/integrated_menubar.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Nanum+Myeongjo&display=swap" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
    .nanum-myeongjo-regular {
      font-family: "Nanum Myeongjo", serif;
      font-weight: 600;
      font-style: normal;
    }
    .black-han-sans-regular {
      font-family: "Black Han Sans", sans-serif;
      font-weight: 700;
      font-style: normal;
    }
    fieldset {
      background-color: #f1eedce5;
      padding: 20px;
      border: 3px solid #e5ddc548;
      border-radius: 20px; 
    }
    .legend {
      background-color: rgba(240, 240, 240, 0.89); 
      color: black;
      padding: 5px; 
      border: 3px solid #E5DDC5; 
      border-radius: 20px; 
    }
    li {
      text-align: left;
      font-weight: bold;
    }
    li.title {
      font-size: larger;
    }
</style>


<template>
    <header>
      <div align="center">
        <section>
          <a href="../mainpage.vue" id="logo" target="_blank"><img src="../image/mainpage_logo.png" style="max-width: 23%"></a>
            <nav>
              <ul>
                <li><a href = "../1_Wei.vue">
                  <i class="black-han-sans-regular">위 魏</i></a></li>
                <li><a href= "../2_Han.vue">
                  <i class="black-han-sans-regular">촉 蜀</i></a></li>
                <li><a href="../3_Wu.vue">
                  <i class="black-han-sans-regular">오 吳</i></a></li>
                <li><a href="../googlemaps.vue">
                  <i class="black-han-sans-regular">상세지도</i></a></li>
              </ul>
            </nav>
        </section>
      </div>
    </header>
        
    <div>
    <h1><a href = "../mainpage.vue"></a></h1>
    <div class="container" align="center">
      <nav>
        <ul class="menu1">
          <li class = "black-han-sans-regular"><a href = "../1_Wei.vue">위 魏</a>
            <ul class = "submenu">
              <li><a href="./위_중원 4주.vue">중원</a></li>
              <li><a href="./위_하북 4주.vue">하북</a></li>
              <li><a href="./위_서북 2주.vue">서북</a></li>
            </ul>
          </li>
        </ul>
        <ul class = "menu2">
          <li class = "black-han-sans-regular"><a href = "../2_Han.vue">촉 蜀</a>
            <ul class = "submenu">
              <li><a href="./촉_익주.vue">익주</a></li>
            </ul>
          </li>
        </ul>
        <ul class = "menu3">
          <li class = "black-han-sans-regular"><a href = "../3_Wu.vue">오 吳</a>
            <ul class = "submenu">
              <li><a href="./오_형주.vue">형주</a></li>
              <li><a href="./오_양주.vue">양주</a></li>
              <li><a href="./오_교주.vue">교주</a></li>
            </ul>
          </li>
        </ul>
        <ul class = "menu4">
          <li class = "black-han-sans-regular"><a href = "../googlemaps.vue">상세지도</a>
          </li>
        </ul>
      </nav>
    </div>
    </div>


    <div>
      <main>
        <h1>중원 4주</h1>
        <table>
          <tr>
            <td><img src="../image/사례.webp" alt="조위"></td>
            <td><img src="../image/연주.webp" alt="조위"></td>
          </tr>
          <tr>
            <td><img src="../image/예주.webp" alt="조위"></td>
            <td><img src="../image/서주.webp" alt="조위"></td>
          </tr>
        </table>
          <p><td>서로 다닥다닥 붙어있는 중원 4주의 모습</td></p>
        <fieldset>
          <legend><r class="legend"><b class = "nanum-myeongjo-regular">중원 4주와 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">사례</li>
              <p class = "nanum-myeongjo-regular">
                후한의 수도 낙양과 그 일대의 수도권으로 삼국지 초기 십상시의 난과 반동탁연합의 주요무대이다.
              </p><br><br>
            
            <li class = "nanum-myeongjo-regular">연주</li>
              <p class = "nanum-myeongjo-regular">
                동으로는 청주, 서로는 서주, 남으로는 사례, 북으로는 기주로 통하는 지역으로 중원에서도 중심에 자리 잡고 있는 땅이다. 조조의 책사 순욱이 연주를 '천하의 중심'이라고 표현한 적이 있을정도로 어디로든 세력을 확장해 나가기 좋은 땅이며 조조가 위나라를 건국하기 전에 이 지역을 통치하는 연주목 지위를 맡은 적이 있을 정도로 전통적인 위나라의 강역이자 조조의 주요 기반지역이다.
              </p><br><br>

            <li class = "nanum-myeongjo-regular">예주</li>
              <p class = "nanum-myeongjo-regular">
                군웅할거 시기 원술의 주요 근거지로 원술이 황제를 참칭한 이후 주변 군웅들의 공격을 받고 패퇴한 이후 조조의 세력권이 되었다. 또다른 특이사항으로는 한고조 유방의 고향이 이 지역에 위치한 패국 패현이라는 점, 유비가 잠시 이 지역의 소패성에 주둔했다는 점이 있다.
              </p><br><br>

            <li class = "nanum-myeongjo-regular">서주</li>
              <p class = "nanum-myeongjo-regular">
                삼국지 초중반(군웅할거~적벽대전)의 주요무대로 유비가 강력한 군벌로 성장하는데 중요한 지역이다. 군웅할거 시기에 당시 도겸이 적극적으로 세력을 확보하여 사실상 도겸의 영역이 되었고 이후 서주목에 임명되었다. 이후 도겸의 후계자가 된 유비가 그 세력을 물려받았고 그 뒤에 서주를 여포에게 빼앗겼다가, 결국 여포를 멸망시킨 조조가 서주를 차지하게 되었다. 이 과정에서 조조는 서주대학살이라는 희대의 악행으로 민심을 돌아서게 만들었고 유비는 서주 → 하북 → 여남 → 형주을 거쳐 세력을 불리고 마침내 익주를 차지하여 촉한을 개국했다.  
              </p>
          </ul>
        </fieldset>
      </main>
    </div>
</template>