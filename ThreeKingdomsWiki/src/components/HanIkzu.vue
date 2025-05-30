
  <style src ="../css/배경.css"></style>
  <style src ="../css/integrated_menubar.css"></style>
  <style src ="../css/App.css"></style>


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
    .quote{
      font-style: italic;
      text-align: left;
      font-weight: bolder;
      color: goldenrod;
      text-shadow: -1px -1px 0 #ffffff,  
                    1px -1px 0 #ffffff,
                  -1px  1px 0 #ffffff,
                    1px  1px 0 #ffffff;
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

    <div><!--main시작-->
      <main>
        <h1>익주 益州</h1>
        <img src="../image/지형도_3D.png" alt="익주3D지형도"><br>
        <p>산맥 사이의 동그라미 친 분지 지역이 익주의 중심 파촉이다</p>
        <fieldset>
          <legend><r class="legend"><b class = "nanum-myeongjo-regular">익주의 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">험난한 지형</li><br>
            <p class = "nanum-myeongjo-regular"><c class = "quote">"촉으로 가는 길은 하늘을 오르기보다 어렵다" 이태백 - 촉도난</c></p>
            <p class = "nanum-myeongjo-regular">
              당나라의 시인 이태백이 촉으로 가는 여정을 묘사한 시의 한 구절이다. 위의 지도에서 볼 수 있듯 익주는 험한 산맥들로 둘러쌓인 분지지형이다.
              따라서 당시 중국의 중심이던 중원지방에서 촉으로 들어오기는 매우 어려웠고, 익주를 근거지로 삼은 유비의 촉나라는 중원일대를 장악하고 국력이 5배 가량 차이났던 조위를 상대로 오랜기간 세력의 균형을 유지할 수 있었다.
            </p><br><br>
            
            <li class = "nanum-myeongjo-regular">촉한정통론의 근거</li><br>
            <p class = "nanum-myeongjo-regular">
              익주는 한나라의 초대황제 유방의 유배지로, 유방은 이 땅을 기반으로 내실을 다지고 천하통일의 기반을 닦아 항우의 초나라를 멸망시키고 중국을 통일했다. 촉한은 이런 '한나라의 계승'을 건국이념으로 한 국가다. 4백여년간 중국을 통치한 통일왕조 한나라는 당대 사람들에게 큰 영향을 미쳤고 위나라의 통치자 조비가 한나라의 마지막 황제 헌제에게 선양을 받아 스스로 황제의 자리에 오른 것은 황제의 자리는 유씨에게만 있다는 생각을 송두리째 뒤바꿔버린 대사건이었다.
              유비는 익주를 차지하고 자신이 유방의 피를 이어받은 한나라의 적법한 후계자임을 내세우며 당대 사람들에게 지지를 얻었다.
            </p><br><br>

            <li class = "nanum-myeongjo-regular">풍부한 자원</li><br>
            <p class = "nanum-myeongjo-regular">
              익주는 산악으로 둘러쌓인 천혜의 요새임과 동시에 농사를 지을 수 있는 땅이 많은 분지지형이다. 농업생산량이 곧 국력과 직결 되던 전근대시대에 이러한 익주의 특징은 촉나라가 중국 최고의 농업생산량을 가진 중원지역을 차지한 위나라에 뒤지지 않게 만들어 주었다.
            </p>
          </ul>
        </fieldset>
      </main>
    </div><!--main끝-->
</template>