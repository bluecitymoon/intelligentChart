(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MediaTypeDeleteController',MediaTypeDeleteController);

    MediaTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MediaType'];

    function MediaTypeDeleteController($uibModalInstance, entity, MediaType) {
        var vm = this;

        vm.mediaType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MediaType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
