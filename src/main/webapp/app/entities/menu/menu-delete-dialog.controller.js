(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuDeleteController',MenuDeleteController);

    MenuDeleteController.$inject = ['$uibModalInstance', 'entity', 'Menu'];

    function MenuDeleteController($uibModalInstance, entity, Menu) {
        var vm = this;

        vm.menu = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Menu.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
