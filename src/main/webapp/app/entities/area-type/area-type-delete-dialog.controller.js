(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('AreaTypeDeleteController',AreaTypeDeleteController);

    AreaTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'AreaType'];

    function AreaTypeDeleteController($uibModalInstance, entity, AreaType) {
        var vm = this;

        vm.areaType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AreaType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
