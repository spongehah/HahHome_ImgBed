$(function () {
    blogSearchResults();
})

function blogSearchResults(pageNum, pageSize) {
    $.ajax({
        url: "/blogSearchResults",
        type: "GET",
        data: {
            "pageNum": pageNum,
            "pageSize": pageSize,
            "title": $("#Search_title_value").val()
        },
        success: function (data) {
            $("#Search_list").empty();
            $.each(data.list, function (index, item) {
                $("<div class=\"ui attached animate__animated animate__fadeInUp\"></div>").append(
                    $("<div class=\"d-web-info-box\" data-wow-duration=\"0.4s\"></div>").append(
                        $("<div class=\"ui mobile stackable grid\"></div>").append(
                            // 博客图片
                            $("<div class=\"five wide column\" style='overflow: hidden;'></div>").append(
                                $("<img  class=\"ui rounded image centered Itemimg\"/>")
                                    .attr("src", item.firstPicture)
                            )
                        ).append(
                            //博客展示内容
                            $("<div class=\"eleven wide column h-padded-td-large\"></div>").append(
                                $("<a></a>").attr("href", "javascript:;").append(
                                    $("<h3 class=\"ui header titleSuspension\"></h3>").append(item.title)
                                ).on("click", function () {
                                    window.location.href = "/toBlog/" + item.id;
                                })
                            ).append(
                                $("<p class=\"m-text\"></p>").append(item.description + "......")
                            ).append(
                                $("<div class=\"ui grid\"></div>").append(
                                    $("<div class=\"wide column\"></div>").append(
                                        $("<div class=\"ui mini horizontal link list\"></div>").append(
                                            $("<div class=\"item\"></div>").append(
                                                $("<div class=\"content\"></div>").append(
                                                    // 博客创建者
                                                    $("<span class=\"header\"></span>").append(
                                                        $("<i class = \"user outline red  icon\" ></i>")
                                                    ).append(item.userName)
                                                )
                                            )
                                        ).append(
                                            //创建时间
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"clock outline blue icon\"></i>")
                                            ).append(item.edittime != null && item.edittime != '' ? " " + item.edittime : " " + item.createtime)
                                        ).append(
                                            // 浏览量
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"eye green icon\"></i>")
                                            ).append(" " + item.views)
                                        ).append(
                                            // 评论量
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"comment alternate outline orange icon\"></i>")
                                            ).append(" " + item.commentCount)
                                        )
                                    )
                                )
                            )
                        )
                    )
                ).appendTo("#Search_list");
            })
            $("#blogPaging").empty();
            if (data.navigatepageNums.length > 1) {
                let div = $("<div class=\"ui pagination menu\"></div>");
                div.append(
                    $("<a class=\"item\">上一页&nbsp;&nbsp;&nbsp;</a>").on("click", function () {
                        blogSearchResults(data.prePage, null);
                    })
                )
                $.each(data.navigatepageNums, function (index, item) {
                    div.append(
                        $("<a class=\"item \"></a>").append(item).addClass(item == data.pageNum ? "active" : "").on("click", function () {
                            blogSearchResults(item, null);
                        })
                    )
                })
                div.append(
                    $("<a class=\"item\">&nbsp;&nbsp;&nbsp;下一页</a>").on("click", function () {
                        blogSearchResults(data.nextPage, null);
                    })
                )
                div.appendTo("#blogPaging");
            }
        }
    })
}

$(document).on("keyup", "#Search_for_articles", function () {
    if (event.keyCode == 13) {
        window.location.href = "/toblogSearch?title=" + $.trim($("#Search_for_articles").val());
    }
})

$(document).on("click", "#search_blog_btn", function () {
    window.location.href = "/toblogSearch?title=" + $.trim($("#Search_for_articles").val());
})