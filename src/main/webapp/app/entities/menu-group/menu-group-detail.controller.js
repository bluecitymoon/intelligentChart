(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuGroupDetailController', MenuGroupDetailController);

    MenuGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MenuGroup', 'Menu'];

    function MenuGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, MenuGroup, Menu) {
        var vm = this;

        vm.menuGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:menuGroupUpdate', function(event, result) {
            vm.menuGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
