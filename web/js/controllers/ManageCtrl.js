/**
 * Created by Ramesses on 2018/12/17
 */

var app = angular.module(PROJECT);


app.controller('ManageCtrl',function($scope,$rootScope,$stateParams,RESTClient,Category,$state,URL)
{
    $rootScope.proId=$stateParams.programId;
    $scope.option=Category.option;
    $scope.volunteer=Category.volunteer;
    console.log($scope.option);
    var CurIndex=-1;
    $scope.check=function(index)
    {
      $scope.tmp=$scope.option[index];
      CurIndex=index;
      $("#CheckModal").modal('show');
    }
    $scope.saveCheck=function(tmp)
    {
      $scope.option[CurIndex]=tmp;
      Category.saveOption(CurIndex,tmp);
      $("#CheckModal").modal('hide');
      prompt("审核成功");
    }
    $scope.cancel=function(index)
    {
      //$scope.volunteer.splice(index,1);
      Category.delVol(index);
      prompt("取消志愿者成功");
    }
 

});