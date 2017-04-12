(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubleMovieSubjectDetailController', DoubleMovieSubjectDetailController);

    DoubleMovieSubjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DoubleMovieSubject'];

    function DoubleMovieSubjectDetailController($scope, $rootScope, $stateParams, previousState, entity, DoubleMovieSubject) {
        var vm = this;

        vm.doubleMovieSubject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:doubleMovieSubjectUpdate', function(event, result) {
            vm.doubleMovieSubject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
