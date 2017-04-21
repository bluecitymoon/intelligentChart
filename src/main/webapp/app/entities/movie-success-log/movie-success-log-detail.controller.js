(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieSuccessLogDetailController', MovieSuccessLogDetailController);

    MovieSuccessLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MovieSuccessLog'];

    function MovieSuccessLogDetailController($scope, $rootScope, $stateParams, previousState, entity, MovieSuccessLog) {
        var vm = this;

        vm.movieSuccessLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:movieSuccessLogUpdate', function(event, result) {
            vm.movieSuccessLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
