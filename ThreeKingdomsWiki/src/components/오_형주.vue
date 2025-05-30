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
        <h1>형주 荊州</h1>
          <img src="../image/형주.webp" alt="형주"><br>
          <p>형주의 위치</p>
        <fieldset>
          <legend><r class="legend"><b class = "nanum-myeongjo-regular">형주의 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">형주의 전략적 중요성</li>
              <p class = "nanum-myeongjo-regular">
                위 지도에서 볼 수 있듯 형주는 당시 삼국의 최중앙부에 위치한 땅이다. 심지어 서쪽으로는 유비의 근거지인 익주, 동으로는 손오의 수도 건업이 위치한 양주, 북으로는 조위의 수도인 낙양이 위치한 사주가 위치해 있다. 따라서 형주를 차지한다면 익주의 험한 산세로 중원으로 나가기 어려웠던 촉은 중원으로 나가는 길목을 얻음과 동시에 위나라의 수도 낙양을 지척에 두고 위를 압박할 수 있었고, 위는 적대세력인 촉,오로부터 수도 낙양까지의 안전지대를 확보함과 동시에 한수를 통해 중원의 막대한 생산력을 바탕으로 양성한 군과 물자를 신속하게 전개시켜 촉,오 연합을 견제할 수 있었다. 오 또한 중원에 비해 개발이 더뎠던 자국의 강남영토에 형주의 생산력을 더하면 삼국통일의 발판을 마련할 수 있었고 지금은 동맹이지만 언제 적으로 변할지 모르는 촉을 견제함과 동시에 강대한 조위를 상대로 수도 건업까지의 안전지대를 확보할 수 있었다. 이처럼 형주는 삼국 모두에게 중요한 지역이었고 이를 둘러싸고 벌어진 형주공방전이 삼국지 중후반(적벽대전~추풍오장원)의 주요 스토리이다.
              </p>
          </ul>
        </fieldset>
      </main>
    </div>
</template>