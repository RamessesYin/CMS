/**
 * Created by Ramesses on 2018/12/17
 */

var app = angular.module(PROJECT);

app.controller('DetailCtrl',function($scope,$rootScope,Category,URL,RESTClient,$timeout,$state,$stateParams,REQUEST,$rootScope)
{
    var programId=$stateParams.programId;
    $scope.info=Category.getOptById(programId);
    console.log($scope.info);
    $rootScope.proId=programId;

    $scope.orderAct=function()
    {
      prompt("预订成功");
    }
    
    


});