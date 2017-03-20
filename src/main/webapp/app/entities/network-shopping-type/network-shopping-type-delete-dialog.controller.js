(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkShoppingTypeDeleteController',NetworkShoppingTypeDeleteController);

    NetworkShoppingTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'NetworkShoppingType'];

    function NetworkShoppingTypeDeleteController($uibModalInstance, entity, NetworkShoppingType) {
        var vm = this;

        vm.networkShoppingType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NetworkShoppingType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
