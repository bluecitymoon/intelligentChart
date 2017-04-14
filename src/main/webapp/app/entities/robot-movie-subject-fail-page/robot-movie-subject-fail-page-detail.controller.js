(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectFailPageDetailController', RobotMovieSubjectFailPageDetailController);

    RobotMovieSubjectFailPageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RobotMovieSubjectFailPage'];

    function RobotMovieSubjectFailPageDetailController($scope, $rootScope, $stateParams, previousState, entity, RobotMovieSubjectFailPage) {
        var vm = this;

        vm.robotMovieSubjectFailPage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:robotMovieSubjectFailPageUpdate', function(event, result) {
            vm.robotMovieSubjectFailPage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
