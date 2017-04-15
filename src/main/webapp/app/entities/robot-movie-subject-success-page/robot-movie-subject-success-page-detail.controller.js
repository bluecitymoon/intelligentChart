(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectSuccessPageDetailController', RobotMovieSubjectSuccessPageDetailController);

    RobotMovieSubjectSuccessPageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RobotMovieSubjectSuccessPage'];

    function RobotMovieSubjectSuccessPageDetailController($scope, $rootScope, $stateParams, previousState, entity, RobotMovieSubjectSuccessPage) {
        var vm = this;

        vm.robotMovieSubjectSuccessPage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:robotMovieSubjectSuccessPageUpdate', function(event, result) {
            vm.robotMovieSubjectSuccessPage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
