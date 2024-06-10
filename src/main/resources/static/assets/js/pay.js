$(document).ready(function () {
    $("#J-Pay").on("click", function () {
        var productId = $(this).data("id");
        $.ajax({
            url: '/pay/weixin_pay_params',
            type: 'post',
            data: { productId: productId },
            success: function (response) {
                var result = response.data;
                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                    "appId": result.appId,
                    "timeStamp": result.timeStamp,
                    "nonceStr": result.nonceStr,
                    "package": result._package,
                    "signType": result.signType,
                    "paySign": result.paySign
                }, function (res) {
                    if (res.err_msg == "get_brand_wcpay_request:ok") {
                        alert('支付成功')
                    }
                });
            },
            error: function (error) {
                alert(error.statusText);
            }
        });
    });
});