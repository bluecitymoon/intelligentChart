(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartMenuDeleteController',ChartMenuDeleteController);

    ChartMenuDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartMenu'];

    function ChartMenuDeleteController($uibModalInstance, entity, ChartMenu) {
        var vm = this;

        vm.chartMenu = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartMenu.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
