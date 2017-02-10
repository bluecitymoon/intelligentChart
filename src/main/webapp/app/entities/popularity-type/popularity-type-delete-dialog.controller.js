(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PopularityTypeDeleteController',PopularityTypeDeleteController);

    PopularityTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PopularityType'];

    function PopularityTypeDeleteController($uibModalInstance, entity, PopularityType) {
        var vm = this;

        vm.popularityType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PopularityType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
