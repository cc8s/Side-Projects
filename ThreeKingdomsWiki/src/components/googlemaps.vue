<style>
  <link rel="stylesheet" href="css/배경.css">
  <link rel="stylesheet" href="css/menubar2.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Nanum+Myeongjo&display=swap" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1">

 
    .nanum-myeongjo-regular {
      font-family: "Nanum Myeongjo", serif;
      font-weight: 600;
      font-style: normal;
    }
    .black-han-sans-regular {
      font-family: "Black Han Sans", sans-serif;
      font-weight: 100000;
      font-style: normal;
      color: aliceblue;
    }
    article {
      text-align: center;
      padding: 100px;
      background-color: transparent;
      color: #000;
    }
    .iframe-wrapper {
      position: relative;
      width: 100%;
      padding-bottom: 56.25%; /* 16:9 aspect ratio */
      height: 0;
    }
    .iframe-wrapper iframe {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border: 0;
    }
</style>

<template>
<header>
  <div align="center">
    <section>
      <a href="./mainpage.vue1_Wei.vue" id="logo" target="_blank"><img src="./image/mainpage_logo.png" style="max-width: 23%"></a>
        <nav>
          <ul>
            <li><a href = "./1_Wei.vue">
              <i class="black-han-sans-regular">위 魏</i></a></li>
            <li><a href= "./2_Han.vue1_Wei.vue">
              <i class="black-han-sans-regular">촉 蜀</i></a></li>
            <li><a href="./3_Wu.vue1_Wei.vue">
              <i class="black-han-sans-regular">오 吳</i></a></li>
            <li><a href="./googlemaps.vue1_Wei.vue">
              <i class="black-han-sans-regular">상세지도</i></a></li>
          </ul>
        </nav>
    </section>
  </div>
</header>

  <body>
    <article>
    <div class="iframe-wrapper">
    <iframe src="https://www.google.com/maps/d/embed?mid=1Aj4nj1c9djirgLAca5eBuUjjn1F91GOM&hl=en_US&ehbc=2E312F" width="80%" height="135%"></iframe>
    </div>
    <p><img src="./image/범례.png" width="auto" height="auto"alt="범례"></p>
    </article>
  </body>
</template>