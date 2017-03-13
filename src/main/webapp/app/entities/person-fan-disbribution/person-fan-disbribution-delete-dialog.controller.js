(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanDisbributionDeleteController',PersonFanDisbributionDeleteController);

    PersonFanDisbributionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFanDisbribution'];

    function PersonFanDisbributionDeleteController($uibModalInstance, entity, PersonFanDisbribution) {
        var vm = this;

        vm.personFanDisbribution = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFanDisbribution.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
