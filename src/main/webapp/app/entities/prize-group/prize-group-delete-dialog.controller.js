(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeGroupDeleteController',PrizeGroupDeleteController);

    PrizeGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrizeGroup'];

    function PrizeGroupDeleteController($uibModalInstance, entity, PrizeGroup) {
        var vm = this;

        vm.prizeGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrizeGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
