(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSupportGroupDeleteController',PersonFanSupportGroupDeleteController);

    PersonFanSupportGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFanSupportGroup'];

    function PersonFanSupportGroupDeleteController($uibModalInstance, entity, PersonFanSupportGroup) {
        var vm = this;

        vm.personFanSupportGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFanSupportGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
