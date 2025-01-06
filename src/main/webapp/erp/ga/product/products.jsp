<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>상품 관리</title>
    <!-- base:css -->
    <link rel="stylesheet" href="/erp/vendors/typicons.font/font/typicons.css">
    <link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="/erp/images/favicon.png" />
    
    <style>
        .modal {
            display: none; /* 기본적으로 숨김 */
            position: fixed; /* 고정 위치 */
            z-index: 1; /* 가장 위에 표시 */
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto; /* 스크롤이 필요한 경우 */
            background-color: rgba(0, 0, 0, 0.4); /* 배경에 투명도 */
        }
        
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 화면 중앙에 배치 */
            padding: 20px;
            border: 1px solid #888;
            width: 15%; /* 모달 너비 */
        }
        
        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            float: right;
            cursor: pointer;
        }
        
        .close:hover, .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <%@ include file="/erp/layout/top_layout.jsp"%>
    
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial -->
        <!-- partial:partials/_sidebar.html -->
        <%@ include file="/erp/layout/side_layout.jsp"%>
        <div class="container-scroller">
            <div class="col-lg-12 grid-margin stretch-card">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">기능 별 권한 설정</h4>
                        <input type="button" value="상품 추가" style="flex: 1; padding: 8px; box-sizing: border-box;" id="add-product-modal">
                        <div class="table-responsive">
                            <table class="table table-hover" id="dynamicTable">
                                <thead>
                                    <tr>
                                        <th>상품 관리 코드</th>
                                        <th>상품 이름</th>
                                        <th>상품 설명</th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- page-body-wrapper ends -->
    </div>
    <%@ include file="/erp/layout/footer_layout.jsp"%>
    
    <!-- 권한 추가 모달 -->
    <div id="productModal" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close" id="roleModalClose">&times;</span>
            <h4>상품 추가</h4>
            <!-- 여기서 권한 추가 폼을 만들면 됩니다 -->
            <input type="text" id="productName" placeholder="상품 이름"><br />
            <br /> 
            <input type="text" id="productSummary" placeholder="상품 설명"><br />
            <button id="saveRole">저장</button>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <!-- container-scroller -->
    <!-- base:js -->
    <script src="/erp/vendors/js/vendor.bundle.base.js"></script>
    <!-- endinject -->
    <!-- inject:js -->
    <script src="/erp/js/off-canvas.js"></script>
    <script src="/erp/js/hoverable-collapse.js"></script>
    <script src="/erp/js/template.js"></script>
    <script src="/erp/js/settings.js"></script>
    <script src="/erp/js/todolist.js"></script>
    
    <script>
        $(document).ready(function() {
            $.ajax({
                url: "/api/v1/product/products",
                method: 'GET',
                contentType: "application/json",
                dataType: "json",
                success: function(obj) {
                    let tableBody = $("#dynamicTable tbody");
                    tableBody.empty();
                    $.each(obj.data.products, function(index, item) {
                        let row = $("<tr></tr>");
                        row.attr("data-editable", "false"); // 기본적으로 수정 불가능

                        let urlInput = $("<input type='text' value='" + item.productSeq + "' disabled />");
                        row.append("<td></td>").find("td").last().append(urlInput);

                        let httpMethodInput = $("<input type='text' value='" + item.productName + "' disabled />");
                        row.append("<td></td>").find("td").last().append(httpMethodInput);

                        let dateInput = $("<input type='text' value='" + item.productSummary + "' disabled />");
                        row.append("<td></td>").find("td").last().append(dateInput);

                        let patchButtonTd = $("<td></td>");
                        let patchButton = $("<button></button>").text("수정");
                        patchButtonTd.append(patchButton);
                        row.append(patchButtonTd);

                        let delButtonTd = $("<td></td>");
                        let delButton = $("<button></button>").text("삭제");
                        delButtonTd.append(delButton);
                        row.append(delButtonTd);

                        tableBody.append(row);

                        patchButton.click(function() {
                            let isEditable = row.attr("data-editable") === "true";
                            row.attr("data-editable", !isEditable);
                            row.find("input").each(function() {
                                $(this).prop("disabled", isEditable);
                            });
                            patchButton.text(isEditable ? "수정" : "저장");

                            if (!isEditable) {
                                patchButton.off('click');
                                patchButton.click(function() {
                                    // 수정된 데이터 가져오기
                                    let updatedData = {
                                        productSeq: item.productSeq,
                                        productName: httpMethodInput.val(),
                                        productSummary: dateInput.val(),
                                    };

                                    $.ajax({
                                        url: "/api/v1/product/product",
                                        method: "PUT",
                                        contentType: "application/json",
                                        data: JSON.stringify(updatedData),
                                        dataType: 'json',
                                        success: function(response) {
                                            alert("수정이 완료되었습니다.");
                                            console.log(response);
                                            patchButton.text("수정");
                                            row.attr("data-editable", "false");
                                            row.find("input").prop("disabled", true);
                                        },
                                        error: function(err) {
                                            alert("수정 중 오류가 발생했습니다.");
                                            console.error(err);
                                        }
                                    });
                                });
                            }
                        });

                        // 삭제 버튼 클릭 이벤트
                        delButton.click(function() {
                            $.ajax({
                                url: "/api/v1/product/product?productSeq=" + item.productSeq,
                                method: "DELETE",
                                dataType: 'json',
                                success: function(response) {
                                    alert("삭제가 완료되었습니다.");
                                    console.log(response);
                                    row.remove();
                                },
                                error: function(err) {
                                    alert("삭제 중 오류가 발생했습니다.");
                                    console.error(err);
                                }
                            });
                        });
                    });
                },
                error: function(err) {
                	console.error(err);
                }
            });
        });
    </script>

    <script>
        $(document).ready(function() {
            // '권한 추가' 버튼 클릭 시 모달 띄우기
            $("#add-product-modal").click(function() {
                $("#productModal").show(); // 권한 추가 모달 표시
            });

            // 권한 추가 모달 닫기 (X 버튼 클릭)
            $("#roleModalClose").click(function() {
                $("#productModal").hide(); // 권한 추가 모달 숨기기
            });

            // 모달 바깥 클릭 시 모달 닫기
            $(window).click(function(event) {
                if ($(event.target).is(".modal")) {
                    $(".modal").hide(); // 모달 외부를 클릭하면 모든 모달 숨기기
                }
            });

            // 권한 추가 저장 버튼 클릭
            $("#saveRole").click(function() {
                let roleData = {
                    productName: $("#productName").val(),
                    productSummary: $("#productSummary").val()
                };

                $.ajax({
                    url: "/api/v1/product/product",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(roleData),
                    success: function(response) {
                        alert("상품이 추가되었습니다.");
                        $("#productModal").hide();
                    },
                    error: function(error) {
                        alert("상품 추가 중 오류가 발생했습니다.");
                        console.error(error);
                    }
                });
            });
        });
    </script>
</body>
</html>
