(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeLevelDeleteController',PrizeLevelDeleteController);

    PrizeLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrizeLevel'];

    function PrizeLevelDeleteController($uibModalInstance, entity, PrizeLevel) {
        var vm = this;

        vm.prizeLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrizeLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
