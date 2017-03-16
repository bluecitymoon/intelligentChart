(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRelationDeleteController',PersonRelationDeleteController);

    PersonRelationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonRelation'];

    function PersonRelationDeleteController($uibModalInstance, entity, PersonRelation) {
        var vm = this;

        vm.personRelation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonRelation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
