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
        <h1>하북 4주</h1>
        <table>
          <tr>
            <td><img src="../image/병주.webp" alt="조위"></td>
            <td><img src="../image/기주.webp" alt="조위"></td>
          </tr>
          <tr>
            <td><img src="../image/청주.webp" alt="조위"></td>
            <td><img src="../image/유주.webp" alt="조위"></td>
          </tr>
        </table>
          <p><td>서로 다닥다닥 붙어있는 하북 4주의 모습</td></p>
        <fieldset>
          <legend><r class="legend"><b>하북 4주와 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">병주</li>
              <p class = "nanum-myeongjo-regular">
                십상시의 난 당시 정원이 세력을 가지고 활동하던 지역으로 이후엔 흉노족과 흑산적이 날뛰는 변방지방으로 전락하여 삼국지에서 거의 다뤄지지 않는다.
              </p><br><br>
            
            <li class = "nanum-myeongjo-regular">기주</li>
              <p class = "nanum-myeongjo-regular">
                원소와 공손찬이 이 지역을 두고 서로 경쟁했다. 경쟁과정에서 공손찬이 이 지역의 역경루라는 성채에서 궁지에 몰려 일가족과 동반자살한 후로 원소의 세력권이 되었다가 관도대전에서 패배한 원소의 사망 이후 혼란상황에서 조조가 하북을 평정하며 조조의 세력권이 된다.
              </p><br><br>

            <li class = "nanum-myeongjo-regular">청주</li>
              <p class = "nanum-myeongjo-regular">
                군웅할거 시기 이 지역을 기반으로한 대형 세력이 등장하지 못했고 조조의 하북평정 과정에서 조조의 세력권이 된다.
              </p><br><br>

            <li class = "nanum-myeongjo-regular">유주</li>
              <p class = "nanum-myeongjo-regular">
                중국의 동북지역과 만주일대를 포함하는 지역으로 공손찬의 기반지역이다. 원소와 경쟁과정에서 공손찬이 죽자 유주 서부는 원소가 차지하였고 이후 조조가 차지했으나 유주 동부지역은 공씨 가문이 계속 세습통치하였다.
              </p>
          </ul>
        </fieldset>
      </main>
    </div>
</template>