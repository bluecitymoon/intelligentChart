(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansPucharsingPowerDeleteController',PersonFansPucharsingPowerDeleteController);

    PersonFansPucharsingPowerDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFansPucharsingPower'];

    function PersonFansPucharsingPowerDeleteController($uibModalInstance, entity, PersonFansPucharsingPower) {
        var vm = this;

        vm.personFansPucharsingPower = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFansPucharsingPower.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
