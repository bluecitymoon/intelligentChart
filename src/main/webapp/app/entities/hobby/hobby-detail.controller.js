(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('HobbyDetailController', HobbyDetailController);

    HobbyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Hobby'];

    function HobbyDetailController($scope, $rootScope, $stateParams, previousState, entity, Hobby) {
        var vm = this;

        vm.hobby = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:hobbyUpdate', function(event, result) {
            vm.hobby = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
