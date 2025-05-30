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
      
    <div>
      <main>
        <h1>교주 交州</h1><br>
          <img src="../image/교주.webp" alt="교주"><br>
          <p>대륙 남부에 위치한 교주의 모습</p>
        <fieldset>
          <legend><r class="legend"><b class = "nanum-myeongjo-regular">교주의 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">오의 변방</li>
              <p class = "nanum-myeongjo-regular">
                교주는 현재의 광둥성·광시 좡족 자치구·하이난성 일대와 베트남 북반부에 해당하는 지역으로 삼국지 후반에 되서야 오나라의 실질적 통치가 미치는 지역이다. 그 이후로도 교주에는 크고 작은 반란이 일어났으며 귀양지로 쓰일정도로 험한 지역이었다.
              </p>
          </ul>
        </fieldset>
      </main>
    </div>
    </div>
</template>