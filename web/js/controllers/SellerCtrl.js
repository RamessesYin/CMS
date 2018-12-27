/**
 * Created by Ramesses on 2018/12/17
 */


var app = angular.module(PROJECT);


app.controller('SellerCtrl',function($scope,$rootScope,RESTClient,Category,$state,URL,$timeout,$stateParams)
{
    $rootScope.proId=$stateParams.programId;
    $scope.FILE_URL=URL.FILE;
    $rootScope.proId=$stateParams.programId;
    $scope.option=Category.option;
    $scope.order=Category.order;
    console.log($scope.option);
    $scope.tmp={};
    $scope.select={};
    var curIndex=-1;
    $scope.editAct=function(index)
    {
      curIndex=index;
      $scope.select=$scope.option[index];
      $("#editActModal").modal('show');
    }
    $scope.saveEdit=function(act)
    {
      act.status=0;
      $scope.option[curIndex]=act;
      //write back the change
      Category.saveOption(curIndex,act);
      $("#editActModal").modal('hide');
      prompt("编辑成功");
    }
    $scope.addAct=function(index)
    {
      $("#addActModal").modal('show');
    }
    $scope.saveAct=function(act)
    {
      act.status=0;
      act.id=$scope.option.length+1;
      $scope.option.push(act);
      Category.addOption(act)
      $("#addActModal").modal('hide');
      //write back the change
      prompt("添加成功");
    }
    $scope.delAct=function(index)
    {


       Category.delOpt(index);
       //write back the change
       prompt("删除成功");
    }
    $scope.change=function(index)
    {
      if($scope.order[index].o_st==1)
      {
        $scope.order[index].o_st=0;
      }
      else
      {
        $scope.order[index].o_st=1;
      }
      //write back the change
      prompt("修改订单状态成功");
      Category.order[index].o_st=$scope.order[index].o_st;
    }
    



});