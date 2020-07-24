function getCookie(cookie_name) {
  var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
  if ( results ) {return ( unescape ( results[2] ) );}
  else { return null;}
}

function setCookie(name, value) {
  document.cookie = name + "=" + escape(value);
}

function setLocale(locale) { 
  setCookie("locale",locale);
  $("script.lang").remove();
  if (locale == "ru") {
    $("head").prepend(`<script id="lang" src="/lang_ru.js"></script>`);
  } else {
    $("head").prepend(`<script id="lang" src="/lang_en.js"></script>`);
  } 
} 

window.onload = pageLoadedHandler;
function pageLoadedHandler() {
   
  locale = getCookie("locale");
  if (locale == null) { locale = "ru";};
  setLocale(locale);
  
  createBodyBookList();
}

function setLang_RU(){
  setLocale("ru");
  createBodyBookList();  
}

function setLang_EN(){
  setLocale("en");
  createBodyBookList();  
}

function loadBookList(mode) {
var URL; 
    
   if (mode == 0) {
     URL = "/book";
   } else {
     URL ="/book/selectby?FindText="+$("#FindText").val()+"&cbFindType="+$("#cbFindType").val();
   };
    
   $.get(URL).done(function (books) {
    
      $("div.listbook").empty();

      books.forEach(function (book) {
        $('div.listbook').append(`
           <ol> 
             <h2> 
               <a onclick="createBodyBook(${book.id})">${book.name} 
                 <span class="author" >${book.authors}</span> 
                 <span class="author">[ </span> 
                 <span class="author">${book.genres}</span> 
                 <span class="author"> ]</span> 
               </a>   
             </h2>
           </ol>  
        `);
      });
    });
}

function createBodyBookList() {

  $("title").remove();
  $("head").append(`<title>${LANG.loc_booklist}</title>`);
 
  $("body").empty();
  $("body").append(`
  
    <div>
       <a class="link" onclick="setLang_EN()">EN</a>
       <a class="link" onclick="setLang_RU()">RU</a>
    </div>
   
    <div id="find" >
      <input id="FindText" type="text" style="width: 45%" placeholder="${LANG.loc_findstirng}" >
      <select id="cbFindType" style="width: 20%">
        <option value="all"    >${LANG.loc_findallbook}</option>
        <option value="name"   >${LANG.loc_findname}</option>
        <option value="author" >${LANG.loc_findauthor}</option>
        <option value="genre"  >${LANG.loc_findgenre}</option>
      </select>
      <button id="buFind"     onclick="loadBookList(1)"   style="width: 10%" >${LANG.loc_find}</button>
      <button id="insertBook" onclick="createBodyBook(0)" style="width: 20%" >${LANG.loc_insertbook}</button>
    </div>
	
    <h1>${LANG.loc_booklist}</h1>
  
    <div class = "listbook">
    </div>
  
  `);    
  
  loadBookList(0);
}

function createBook(book) {

  $("body").empty();
  $("body").append(`

      <h3>
         <a class="link"  onclick="createBodyBookList()">${LANG.loc_backlist}</a>
      </h3>

      <h1>${LANG.loc_book}</h1>

      <div>
        <p>${LANG.loc_tittle}</p>
        <input id="BookName" type="text" class="name" placeholder="${LANG.loc_tittlehelp}" value="${book.name}">

        <p>${LANG.loc_author}</p>
        <input id="Author"   type="text" class="name" placeholder="${LANG.loc_authorhelp}" value="${book.authors}">

        <p>${LANG.loc_genre}</p>
        <input id="Genre"    type="text" class="name" placeholder="${LANG.loc_genrehelp}" value="${book.genres}">

      </div>
    `);

  if (book.id != 0) {
    $("body").append(`
      <div id="modif" align="right" >
        <button onclick="updateBook(${book.id})">${LANG.loc_update}</button>
        <button onclick="deleteBook(${book.id})">${LANG.loc_delete}</button>
      </div>
    `);

    $("body").append(`
            <div class="comments">
            </div>
          `);

    loadCommentList(book.id);

  } else {
    $("body").append(`
      <div id="modif" align="right" >
        <button onclick="insertBook()">${LANG.loc_insert}</button>
      </div>
    `);
  };

}


function createBodyBook(bookid) {
   
  $("title ").remove();
  $("head").append(`<title>${LANG.loc_book}</title>`);    
    
  if (bookid != 0) {
    $.get("book/"+bookid).done(function (book) {
      createBook(book)});
  } else {
    book = {
       id:0,
       name:"",
       authors:"",
       genres:""
    }
    createBook(book);
  };
}

function loadCommentList(bookid) {

   $.get('/comment/search/find?bookID='+bookid).done(function (comments) {
        $("div.comments").empty();
    
        if (comments._embedded.comments.length != 0) {
          $("div.comments").append(`<p>${LANG.loc_comment}</p>`); 
          
          comments._embedded.comments.forEach(function (comment) {
              $('div.comments').append(`
            
              <ol>   
                <p class = "comment" >${comment.comment}</p>
                <p class = "commentdel"> 
                   <button  onclick="deleteComment('${comment._links.self.href}', ${bookid})">${LANG.loc_delete}</button>
                </p>
              </ol>
              `);
          });
        } else {
          $("div.comments").append(`<p> </p>`); 
        }; 
        
       $('div.comments').append(`
           <p>${LANG.loc_commented}<Br>
             <input class = "comment" id = "comment" type="text" placeholder="${LANG.loc_commenthelp}">
             <button onclick="insertComment(${bookid})">${LANG.loc_insert}</button> 
           </p>
       `);
    });
        

}

function insertBook(){
 
  $.ajax({
      url: "/book",
      type: "POST",
      contentType: 'application/json',
      dataType: 'json',
      data: JSON.stringify({
                             name: $("#BookName").val() , 
                             authors: $("#Author").val() , 
                             genres: $("#Genre").val()
                           })
      }).done(function() {createBodyBookList();});
}

function updateBook(bookid){
 
  $.ajax({
      url: "/book",
      type: "PUT",
      contentType: 'application/json',
      dataType: 'json',
      data: JSON.stringify({
                             id : bookid,
                             name: $("#BookName").val() , 
                             authors: $("#Author").val() , 
                             genres: $("#Genre").val()
                           })
      }).done(function() {createBodyBookList();});
}

function deleteBook(bookid){
 
  $.ajax({
      url: "/book/"+bookid,
      type: "DELETE"
    }).done(function() {createBodyBookList();});
}

function insertComment(bookid) {
 
  $.ajax({
      url: "/comment",
      type: "POST",
      contentType: 'application/json',
      dataType: 'json',
      data: JSON.stringify({
                            bookID: bookid , 
                            comment: $("#comment").val()
                          })
      }).done(function() {createBodyBook(bookid);});
}

function deleteComment(id, bookid) {
 
  $.ajax({
      url: id,
      type: "DELETE"
  }).done(function() {createBodyBook(bookid);});
}