(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonInnovationDeleteController',PersonInnovationDeleteController);

    PersonInnovationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonInnovation'];

    function PersonInnovationDeleteController($uibModalInstance, entity, PersonInnovation) {
        var vm = this;

        vm.personInnovation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonInnovation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
