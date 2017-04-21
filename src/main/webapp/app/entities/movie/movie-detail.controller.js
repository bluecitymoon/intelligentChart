(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieDetailController', MovieDetailController);

    MovieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Movie'];

    function MovieDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Movie) {
        var vm = this;

        vm.movie = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('intelligentChartApp:movieUpdate', function(event, result) {
            vm.movie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
