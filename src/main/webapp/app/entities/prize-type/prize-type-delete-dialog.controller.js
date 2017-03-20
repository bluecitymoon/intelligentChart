(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeTypeDeleteController',PrizeTypeDeleteController);

    PrizeTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrizeType'];

    function PrizeTypeDeleteController($uibModalInstance, entity, PrizeType) {
        var vm = this;

        vm.prizeType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrizeType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
