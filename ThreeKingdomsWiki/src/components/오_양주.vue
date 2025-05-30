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
        <h1>양주 揚州</h1>
          <img src="../image/양주(강동).webp" alt="양주" /><br>
          <p>장강 이남에 위치한 양주의 지도</p>
        <fieldset>
          <legend><r class="legend"><b class = "nanum-myeongjo-regular">양주(강동)의 특징</b></r></legend>
          <ul align = "left">
            <li class = "nanum-myeongjo-regular">들어가며</li>
              <p class = "nanum-myeongjo-regular">
                위의 서북 2주의 양주와는 다른 지역이다. 오의 양주는 揚州 위의 양주는 涼州로 한자가 다르다.
              </p><br><br>
            <li class = "nanum-myeongjo-regular">양주의 기후</li>
              <p class = "nanum-myeongjo-regular">
                장강 이남에 위치한 양주는 중원과는 다른 아열대 기후를 가지고 있었다. 온난한 기후는 수많은 풍토병의 기원이 되었고 의학기술이 발전 되지 않았던 전근대 시대에 이런 특징은 이 지역을 공략하려했던 중원세력(한족)에게 수없는 패배를 안겨줬다. 실제로 장강 이남 지역은 현대까지도 아열대 기후대에서 가축과 야생동물, 그리고 인간이 함께 거주하면서 새로운 변종 바이러스가 계속 만들어지는 곳이다. H5N1조류독감(발원지:후난성=형주 남부)이나 코로나 19(발원지:우한시=형주 강하군)가 이 곳에서 최초로 발견되었다.
              </p><br><br>
            <li class = "nanum-myeongjo-regular">의외의 생산력</li>
              <p class = "nanum-myeongjo-regular">
                물론 고대부터 중국의 중심이던 중원에 비할바는 안되었지만 이 지역의 생산력도 만만치 않았다. 해안가에 접한 주였기 때문에 소금이 특히 많이 생산되었다. 로마에서는 병사들의 급여로 소금을 지급한 적이 있었고 "1량의 백은은 1량의 소금과 같다."라는 격언이 존재할 정도로 전근대시대에 소금은 귀한 물품이었다. 뿐만 아니라 그 비옥하다는 양쯔강(장강) 삼각주가 속한 곳이 바로 이 지역이다.
              </p>
          </ul>
        </fieldset>
      </main>
    </div>
</template>