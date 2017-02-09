(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MenuGroupDeleteController',MenuGroupDeleteController);

    MenuGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'MenuGroup'];

    function MenuGroupDeleteController($uibModalInstance, entity, MenuGroup) {
        var vm = this;

        vm.menuGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MenuGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
