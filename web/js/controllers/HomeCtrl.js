/**
 * Created by Ramesses on 2018/12/17
 */
var app = angular.module(PROJECT);


app.controller('HomeCtrl',function($scope,$rootScope,$stateParams,$http,RESTClient,Category,$state,URL)
{
	$scope.sourceUrl=URL.RESOURCE;
    $scope.videoRecord={};
    $scope.more = function (id)
    {
        $state.$state.go('category',{cateId:id});
    }


    $scope.GoUser=function()
    {

        $state.go('user',{programId:-1});
    }

    $scope.ToUser=function(info)
    {
        

        $("#SignInModal").modal('hide');

    }

    $scope.GoSeller=function()
    {
        

        $state.go('seller',{programId:-1});

    }


    $scope.GoRoot=function()
    {
        $state.go('manage',{programId:-1});

    }
        
   

    $scope.SignIn=function(info)
    {
        log(info);
/*        if(video.remark==null || video.remark.length==0)
        {
            prompt('请输入文件名！');
        }*/

          $("#SignInModal").modal('hide');
          prompt('登录成功');



    }


     $scope.Register=function(info)
    {
        log(info);

         RESTClient.getResource(URL.REQUEST+'User')
            .then(
            function(data)
            {
                prompt('注册成功，请登录');
            });


          $("#RegisterModal").modal('hide');


    }

});
