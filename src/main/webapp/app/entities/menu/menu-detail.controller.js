(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuDetailController', MenuDetailController);

    MenuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Menu', 'MenuGroup'];

    function MenuDetailController($scope, $rootScope, $stateParams, previousState, entity, Menu, MenuGroup) {
        var vm = this;

        vm.menu = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:menuUpdate', function(event, result) {
            vm.menu = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
