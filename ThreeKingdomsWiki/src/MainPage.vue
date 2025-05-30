

  <style src ="./css/배경.css"></style>
  <style src ="./css/flip.css"></style>
  <style src ="./css/delunderline.css"></style>
  <style src ="./css/menubar2.css"></style>
  <style src ="./css/App.css"></style>

<template>
  <header>
    <div align="center">
      <section>
        <a href="./mainpage.vue" id="logo" target="_blank"><img src="./image/mainpage_logo.png" style="max-width: 23%"></a>
          <nav>
            <ul>
              <li><a href = "./1_Wei.vue">
                <i class="black-han-sans-regular">위 魏</i></a></li>
              <li><a href= "./2_Han.vue">
                <i class="black-han-sans-regular">촉 蜀</i></a></li>
              <li><a href="./3_Wu.vue">
                <i class="black-han-sans-regular">오 吳</i></a></li>
              <li><a href="./googlemaps.vue">
                <i class="black-han-sans-regular">상세지도</i></a></li>
            </ul>
          </nav>
      </section>
    </div>
  </header>
  
  <div>
  <article>
    <div class="cards-container">
      <div class="flip-card">
        <div class="flip-card-inner">
          <div class="flip-card-front">
            <img src="./image/wei.jpg" alt="Wei" style="width:300px;height:400px;">
            <h1 style="font-family: Black Han Sans;">위 魏</h1>  
          </div>
          <div class="flip-card-back1">
            <h1 style="font-family: Black Han Sans;"><a href = "./1_Wei.vue"><br><br><br><br>위 魏</a></h1>
            <p class = "nanum-myeongjo-regular"><a href="./위_중원 4주.vue">중원 4주</a></p>
            <p class = "nanum-myeongjo-regular"><a href="./위_하북 4주.vue">하북 4주</a></p>
            <p class = "nanum-myeongjo-regular"><a href="./위_서북 2주.vue">서북 2주</a></p>
          </div>
        </div>
      </div>
    </div>

    <div class="cards-container">
      <div class="flip-card">
        <div class="flip-card-inner">
          <div class="flip-card-front">
            <img src="./image/han.jpg" alt="han" style="width:300px;height:400px;">
            <h1 style="font-family: Black Han Sans;">촉 蜀</h1>
          </div>
          <div class="flip-card-back2">
            <h1 style="font-family: Black Han Sans;"><a href = "./2_Han.vue"><br><br><br><br>촉 蜀</a></h1>
            <p class = "nanum-myeongjo-regular"><router-link to="/HanIkzu">익주</router-link></p>	
          </div>
        </div>
      </div>
    </div>

    <div class="cards-container">
      <div class="flip-card">
        <div class="flip-card-inner">
          <div class="flip-card-front">
            <img src="./image/wu.jpg" alt="wu" style="width:300px;height:400px;">
            <h1 style="font-family: Black Han Sans;">오 吳</h1>
          </div>
          <div class="flip-card-back3">
            <h1 style="font-family: Black Han Sans;"><a href = "./3_Wu.vue"><br><br><br><br>오 吳</a></h1>
            <p class = "nanum-myeongjo-regular"><a href="./오_형주.vue">형주</a></p>
            <p class = "nanum-myeongjo-regular"><a href="./오_양주.vue">양주</a></p>
            <p class = "nanum-myeongjo-regular"><a href="./오_교주.vue">교주</a></p>
          </div>
        </div>
      </div>
    </div>
  </article>

  <div>
        <hr /> 
        
        <hr /> 
      </div>
  </div>  
     
  <footer>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/podgA15ihxs?si=NSkeH2p7PlrAwI5a" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
    </iframe>
  </footer>
</template>